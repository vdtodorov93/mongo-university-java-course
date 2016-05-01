	db.messages.aggregate([
	{$project:{"headers.From":1, "headers.To":1}},
	{$unwind:"$headers.To"},
	{$group:{"_id":{_id:"$_id", "from":"$headers.From", "to": "$headers.To"}}},
	{$group: { _id : {"from" : "$_id.from", "to" : "$_id.to"}, sum:{$sum:1}}},
	{$sort:{sum:-1}}
	], {allowDiskUse:true, cursor:{}})
