@Entity
@Table(name = "organizer")
public class Organizer {
    @Id
    private int id;
    private String name;
    private String email;
    private String telephone;

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;
}