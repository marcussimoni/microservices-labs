#

files=("postgres-bookstore-purchase-connector-source" "postgres-payments-confirmed-connector-source" "postgres-payments-declined-connector-source" "postgres-shipping-confirmed-connector-source")

for file in "${files[@]}"; do

curl -X DELETE http://localhost:9094/connectors/$file
echo "\n $file connections successfuly deleted" 

done

