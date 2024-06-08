import os
import pickle
import re

import networkx as nx
import pandas as pd
import rdflib
from rdflib.term import URIRef, Literal, BNode


def get_value(entity):
    if isinstance(entity, rdflib.URIRef):
        return str(entity)
    elif isinstance(entity, rdflib.Literal):
        if str(entity._datatype) == "http://dbpedia.org/datatype/usDollar":
            return f"${float(str(entity))}"
        elif entity._datatype:
            return str(entity)
        elif entity._language:
            return f"{entity.value}@{entity._language}"
        else:
            return entity.value


def rdf_to_tuple(triple):
    s = get_value(triple[0])
    p = get_value(triple[1])
    o = get_value(triple[2])
    return s, p, o


nt_triple_pattern = re.compile(
    r'(<[^>]+>|"_:[^ ]+"|<[^>]*>|"[^"]*"(@[^ ]+|\^\^<[^>]+>)?)\s(<[^>]+>|[^ ]*)\s(<[^>]+>|"[^"]*"(@[^ ]+|\^\^<[^>]+>)?)\s\.\s*')


def parse_nt_line(line):
    match = nt_triple_pattern.match(line)
    if match:
        subject = match.group(1)
        predicate = match.group(3)
        object_ = match.group(4)

        def parse_term(term):
            if term.startswith('<') and term.endswith('>'):
                return URIRef(term[1:-1])
            elif term.startswith('_:'):
                return BNode(term)
            elif term.startswith('"'):
                if '^^' in term:
                    value, datatype = term.rsplit('^^', 1)
                    return Literal(value.strip('"'), datatype=URIRef(datatype.strip('<>')))
                elif '@' in term:
                    value, lang = term.rsplit('@', 1)
                    return Literal(value.strip('"'), lang=lang)
                else:
                    return Literal(term.strip('"'))
            else:
                return URIRef(term)

        return (parse_term(subject), URIRef(predicate.strip('<>')), parse_term(object_))
    return None


def get_graph(df: pd.DataFrame, dataset_name="dbpedia", root_nodes: set = None):
    graph = nx.MultiDiGraph()
    for i, row in df.iterrows():
        eid = row["eid"]
        class_name = row["class"]
        euri = row["euri"]
        elabel = row["elabel"]
        if root_nodes and euri not in root_nodes:
            continue

        if dataset_name in euri:
            graph.add_node(euri, eid=eid, label=elabel, category=class_name, is_root=True)

    return graph


def add_edges(G: nx.MultiDiGraph, base_path: str, root_nodes=None):
    if not root_nodes:
        root_nodes = [node for node in G.nodes()]
    for root_node in root_nodes:
        data = G.nodes[root_node]
        eid = data["eid"]
        for root_path, dirs, files in os.walk(os.path.join(base_path, str(eid))):
            for file in files:
                if file == f"{eid}_desc.nt":
                    with open(os.path.join(root_path, file), 'r') as nt_file:
                        for line in nt_file:
                            line = line.strip()
                            if line and line.endswith('.'):
                                triple = parse_nt_line(line)
                                if triple:
                                    s, p, o = rdf_to_tuple(triple)
                                    if s not in root_nodes and o not in root_nodes:
                                        continue
                                    if not G.has_edge(s, o, key=p):
                                        G.add_edge(s, o, key=p, predicate=p)
                                    else:
                                        print(f"Edge already exists: {s} {p} {o}")


def mark_summaries(G: nx.MultiDiGraph, base_path: str, root_nodes=None):
    if not root_nodes:
        root_nodes = [node for node, data in G.nodes(data=True) if data.get("is_root", False)]
    for root_node in root_nodes:
        data = G.nodes[root_node]
        eid = data["eid"]

        for root_path, dirs, files in os.walk(os.path.join(base_path, str(eid))):
            for file in files:
                if file.startswith(f"{eid}_gold_top"):
                    match = re.search(r"gold_top(\d+)_(\d+)", file)
                    top_k = match.group(1)
                    index = match.group(2)
                    counter = 0
                    with open(os.path.join(root_path, file), 'r') as nt_file:
                        for line in nt_file:
                            line = line.strip()
                            if line and line.endswith('.'):
                                triple = parse_nt_line(line)
                                if triple:
                                    s, p, o = rdf_to_tuple(triple)
                                    if s not in root_nodes and o not in root_nodes:
                                        continue
                                    G.edges[s, o, p][f"in_gold_top{top_k}_{index}"] = counter
                                    G.edges[s, o, p]["summary_for"] = root_node
                                    counter += 1


def save_graph(G: nx.MultiDiGraph, path: str):
    with open(path + ".pkl", "wb") as f:
        pickle.dump(G, f)
    nx.write_graphml(G, path + ".graphml")


def create_graph(elist_df, dataset_name, dataset_dir, save_name, input_files_base_path, output_files_base_path,
                 root_nodes=None):
    graph = get_graph(elist_df, dataset_name, root_nodes)
    add_edges(graph, os.path.join(input_files_base_path, dataset_dir), root_nodes)
    mark_summaries(graph, os.path.join(input_files_base_path, dataset_dir), root_nodes)
    save_graph(graph, os.path.join(output_files_base_path, save_name))
    return graph
