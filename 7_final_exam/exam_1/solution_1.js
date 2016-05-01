db.messages.count({"headers.From" : "andrew.fastow@enron.com", "headers.To":"jeff.skilling@enron.com"})




find from: andrew.fastow@enron.com
to: jeff.skilling@enron.com

ref: from andrew.fastow@enron.com
to: john.lavorato@enron.com    =  1

db.messages.count({"headers.From" : "andrew.fastow@enron.com", "headers.To":"john.lavorato@enron.com"})