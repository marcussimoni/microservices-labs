#

config_dir="../configs/kafka/connectors"

files=("$config_dir"/*.json)

for file in "${files[@]}"; do

echo "configuring $file"
curl -X POST  -H  "Content-Type:application/json" http://localhost:9094/connectors -d @"$file"
echo "\n" 

done

