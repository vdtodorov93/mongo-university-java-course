package course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {

        // DONE
    	Document queryByPermalink = new Document("permalink", permalink);
        Document post = postsCollection.find(queryByPermalink).first();

        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {

        // DONE
        // Return a list of DBObjects, each one a post from the posts collection
    	Document sortByDate = new Document("date", -1);
        List<Document> posts = new ArrayList<Document>();
        FindIterable<Document> it = postsCollection.find().sort(sortByDate).limit(limit);
        for(Document doc: it) {
        	posts.add(doc);
        }

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        // DONE
        // Remember that a valid post has the following keys:
        // author, body, permalink, tags, comments, date
        //
        // A few hints:
        // - Don't forget to create an empty list of comments
        // - for the value of the date key, today's datetime is fine.
        // - tags are already in list form that implements suitable interface.
        // - we created the permalink for you above.

        // Build the post object and insert it
        Document post = new Document("author", username);
        post.append("title", title);
        post.append("body", body);
        post.append("permalink", permalink);
        post.append("tags", tags);
        post.append("comments", new ArrayList<String>());
        post.append("date", new Date());
        postsCollection.insertOne(post);

        return permalink;
    }




    // White space to protect the innocent








    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

    	Document post = findByPermalink(permalink);
    	Document comment = new Document("author", name);
    	comment.append("body", body);
    	if(email != null && email.length() > 0) {
    		comment.append("email", email);
    	}
    	
    	BasicDBObject updateCommand = new BasicDBObject();
    	updateCommand.put("$push", new BasicDBObject("comments", comment));
    	postsCollection.updateOne(new Document("_id", post.get("_id")), updateCommand);
    	
//    	postsCollection.updateOne(new Document("_id", post.get("_id")), post);
    	
        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments
    }
}
