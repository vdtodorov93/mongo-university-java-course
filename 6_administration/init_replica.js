config = { _id: "rs1", members:[
	{_id: 0, host:"localhost:27018", priority:0, slaveDelay:5},
	{_id: 1, host:"localhost:27019"},
	{_id: 0, host:"localhost:27020"} ]
}

rs.initiate(config)
rs.status()

