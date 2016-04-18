use blog

db.zips.aggregate([
	{ $group : { _id : {"state" : "$state", "city" : "$city" }, 
				pop : { $sum : "$pop" }
			}
	},
	{ $match : { pop : { $gt : 25000 }}},
	{ $match : { "_id.state" : { $in : ["CA", "NY"] }}},
	{ $group : { _id : null, average: { $avg: "$pop"}} }
])