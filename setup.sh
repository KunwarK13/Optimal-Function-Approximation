#!/bin/bash

python_path=$1
venv_dir="$PWD"/py_env
$python_path -m venv "$venv_dir"

"$venv_dir"/bin/pip3 install -r "$PWD"/requirements.txt
