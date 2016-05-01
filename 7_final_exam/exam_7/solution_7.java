public class Cleanup {
	public static void main(String[] args) {
		final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
        final MongoDatabase blogDatabase = mongoClient.getDatabase("photo");
        final MongoCollection<Document> albumsCollection = blogDatabase.getCollection("albums");
        final MongoCollection<Document> imagesCollection = blogDatabase.getCollection("images");
        
        List<Document> images = (List<Document>) imagesCollection.find().into(new ArrayList<Document>());
        for(Document image: images) {
        	int imageId = image.getInteger("_id", 0);
        	long countAlbumsForImage = albumsCollection.count(new Document("images", imageId));
        	if(countAlbumsForImage == 0) {
        		imagesCollection.deleteOne(eq("_id", imageId));
        	}
        }
	}
}