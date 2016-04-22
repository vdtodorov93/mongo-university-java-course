mkdir -p /data/rs1 /data/rs2 /data/rs3
mongod --replSet rs1 --logpath "1.log" --dbpath /data/rs1 --port 27020 --fork
mongod --replSet rs1 --logpath "2.log" --dbpath /data/rs2 --port 27018 --fork
mongod --replSet rs1 --logpath "3.log" --dbpath /data/rs3 --port 27019 --fork
