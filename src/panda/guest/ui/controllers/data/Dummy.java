package panda.guest.ui.controllers.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import panda.host.models.Post;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

public class Dummy {
    public static ObservableList<Post> posts = FXCollections.observableArrayList(
        new Post(
                "mbappe.frank18@myiuc.com",
                "Hey, this is the first post.",
                "btech;swe",
                new File("C:/Users/hp/Documents/CAHIER DE CHARGE.docx"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(LocalDateTime.now().minusDays(1))
        ),
        new Post(
                "ken.miller18@myiuc.com",
                "Hey, this is the second post.",
                "btech;it",
                new File("C:/Users/hp/Documents/sd.pdf"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(2)),
                Timestamp.valueOf(LocalDateTime.now().minusDays(2))
        ),
        new Post(
                "annalise.keating18@myiuc.com",
                "Hey, this is the third post.",
                "btech;swe",
                new File("C:/Users/hp/Documents/ChiffrementRSA.xlsx"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(4)),
                Timestamp.valueOf(LocalDateTime.now().minusDays(3))
        ),
        new Post(
                "jujutsu.kaizen18@myiuc.com",
                "Hey, this is the fourth post.",
                "btech;it",
                new File("C:/Users/hp/Documents/PRÉSENTATION RAPPORT OVER SOFT SOLUTION.pptx"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(LocalDateTime.now().minusDays(1))
        ),
        new Post(
                "harvey.specter18@myiuc.com",
                "Hey, this is the fifth post.",
                "btech;swe",
                new File("C:/Users/hp/Documents/angular-app.rar"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(5)),
                Timestamp.from(Instant.now())
        ),
        new Post(
                "khal.drogo18@myiuc.com",
                "Hey, this is the last post.",
                "btech;it",
                new File("C:/Users/hp/Documents/gantt_es.png"),
                Timestamp.valueOf(LocalDateTime.now().minusDays(1)),
                Timestamp.from(Instant.now())
        )
    );
}
