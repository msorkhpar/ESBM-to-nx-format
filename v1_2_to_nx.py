import os
from collections import defaultdict
from typing import List, Dict

import pandas as pd
from utils import create_graph

input_files_base_path = "./data/ESBM/V1.2/"
output_files_base_path = "./data/output/V1.2"

os.makedirs(output_files_base_path, exist_ok=True)

elist_df = pd.read_csv(os.path.join(input_files_base_path, "elist.txt"), sep="\t", usecols=[
    "eid", "dataset", "class", "euri", "elabel", "tripleNum"
])


def fold_group(fold_base_path):
    fold_group = defaultdict(set)
    fold_table = []
    with open(os.path.join(fold_base_path, "readme.txt"), "r") as f:
        f.readline()
        for i, line in enumerate(f):
            fold_data = line.split("\t")
            train = list(map(lambda s: int(s.strip()[1]), fold_data[1].strip().split(",")))
            val = int(fold_data[2].strip()[1])
            test = int(fold_data[3].strip()[1])
            fold_table.append((train, val, test))

    for i in range(5):
        s_path = os.path.join(fold_base_path, f"S{i}.txt")
        with open(s_path, "r") as f:
            for line in f:
                entity_id = line.split("\t")[-1].strip()
                fold_group[i].add(entity_id)

    return fold_group, fold_table


def create_fold_graphs(elist_df, dataset, dataset_dir, fold_data: Dict[int, set], fold_table: List[tuple]):
    for i, (train, val, test) in enumerate(fold_table):
        train_nodes = fold_data[train[0]].union(fold_data[train[1]]).union(fold_data[train[2]])
        g_train = create_graph(elist_df, dataset, dataset_dir, f"v1_2_{dataset}_train_{i}.pkl", input_files_base_path,
                               output_files_base_path, train_nodes)
        print(g_train)
        g_val = create_graph(elist_df, dataset, dataset_dir, f"v1_2_{dataset}_val_{i}.pkl", input_files_base_path,
                             output_files_base_path, fold_data[val])
        print(g_val)
        g_test = create_graph(elist_df, dataset, dataset_dir, f"v1_2_{dataset}_test_{i}.pkl", input_files_base_path,
                              output_files_base_path, fold_data[test])
        print(g_test)


dbpedia = create_graph(elist_df, "dbpedia", "dbpedia_data", "v1_2_dbpedia_full.pkl",
                       input_files_base_path, output_files_base_path,
                       )
print(dbpedia)
lmdb = create_graph(elist_df, "linkedmdb", "lmdb_data", "v1_2_linkedmdb_full.pkl",
                    input_files_base_path, output_files_base_path, )
print(lmdb)

dbpedia_fold_data, dbpedia_fold_table = fold_group(os.path.join(input_files_base_path, "dbpedia_split"))
create_fold_graphs(elist_df, "dbpedia", "dbpedia_data", dbpedia_fold_data, dbpedia_fold_table)

lmdb_fold_data, lmdb_fold_table = fold_group(os.path.join(input_files_base_path, "lmdb_split"))
create_fold_graphs(elist_df, "linkedmdb", "lmdb_data", lmdb_fold_data, lmdb_fold_table)
