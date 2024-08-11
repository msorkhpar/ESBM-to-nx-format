import os

import networkx as nx
import pandas as pd

import utils

input_files_base_path = "./data/ESBM_plus/"
output_files_base_path = "./data/output/ESBM_plus/"

os.makedirs(output_files_base_path, exist_ok=True)

elist_df = pd.read_csv(os.path.join(input_files_base_path, "elist.txt"), sep="\t", usecols=[
    "eid", "dataset", "class", "euri", "elabel", "tripleNum"
])


def add_edges(G: nx.MultiDiGraph, base_path: str, file_name: str, root_nodes=None):
    with open(os.path.join(base_path, file_name), 'r') as nt_file:
        count = 0
        for line in nt_file:
            line = line.strip()
            if line and line.endswith('.'):
                triple = utils.parse_nt_line(line)
                if triple:
                    count += 1
                    s, p, o = utils.rdf_to_tuple(triple)
                    try:
                        if not G.has_edge(s, o, key=p):
                            G.add_edge(s, o, key=p, predicate=p)
                    except Exception as e:
                        print(e)


def create_graph(dataset_name, dataset_dir, save_name, file_name):
    graph: nx.MultiDiGraph = utils.get_graph(elist_df, dataset_name)
    add_edges(graph, os.path.join(input_files_base_path, dataset_dir), file_name)
    utils.mark_summaries(graph, os.path.join(input_files_base_path, dataset_dir))
    graph = utils.save_graph(graph, os.path.join(output_files_base_path, save_name))
    return graph


debpedia = create_graph("dbpedia", "dbpedia_data", "Plus_dbpedia_full", "complete_rdf_dbpedia.nt")
print(debpedia)
lmdb = create_graph("linkedmdb", "lmdb_data", "Plus_linkedmdb_full", "complete_rdf_lmdb.nt")
print(lmdb)
