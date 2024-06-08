import os
import pandas as pd

from utils import create_graph

input_files_base_path = "./data/ESBM/V1.1/"
output_files_base_path = "./data/output/V1.1"

os.makedirs(output_files_base_path, exist_ok=True)

elist_df = pd.read_csv(os.path.join(input_files_base_path, "elist.txt"), sep="\t", usecols=[
    "eid", "dataset", "class", "euri", "elabel", "tripleNum"
])

dbpedia = create_graph(elist_df, "dbpedia", "dbpedia", "v1_1_dbpedia_full.pkl",
                       input_files_base_path, output_files_base_path)
print(dbpedia)

lmdb = create_graph(elist_df, "linkedmdb", "lmdb", "v1_1_linkedmdb_full.pkl",
                    input_files_base_path, output_files_base_path)
print(lmdb)
