package mongocourse.homework3;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;
import com.mongodb.assertions.Assertions;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		final Morphia morphia = new Morphia();
		morphia.mapPackage("mongocourse.homework3");
		final Datastore datastore = morphia.createDatastore(new MongoClient(), "school");
		datastore.ensureIndexes();

		Query<Student> query = datastore.createQuery(Student.class);
		List<Student> students = query.asList();
		System.out.println(students.size());
		for (Student student : students) {
			student.getScores();
			Score lowestHomeworkScore = null;
			for (Score score : student.getScores()) {
				if (score.getType().equals("homework")) {
					if (lowestHomeworkScore == null || lowestHomeworkScore.getScore() > score.getScore()) {
						lowestHomeworkScore = score;
					}
				}
			}
			
			student.getScores().remove(lowestHomeworkScore);
			Assertions.isTrue("SIZE", student.getScores().size() == 3);
			//Query<Student> findById = datastore.createQuery(Student.class).field("_id").equal(student.getId());
			UpdateOperations<Student> update = datastore.createUpdateOperations(Student.class).set("scores", student.getScores());
			System.out.println("Updating: " + student.getName());
			System.out.println("Deleting: " + lowestHomeworkScore);
			//datastore.update(findById, update);
			datastore.updateFirst(datastore.createQuery(Student.class).field("id").equal(student.getId()), update);
//			datastore.

			
//			datastore.save(student);
		}
		
		System.out.println(datastore.getCollection(Student.class).count());
	}
}
