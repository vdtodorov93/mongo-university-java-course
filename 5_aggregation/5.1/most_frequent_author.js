use blog
db.posts.aggregate([
	{$unwind:"$comments"},
	{$project:{_id:0, 'comment':"$comments"}},
	{$group:{_id:"$comment.author", "posts":{$sum:1}}},
	{$sort:{"posts":-1}}
])