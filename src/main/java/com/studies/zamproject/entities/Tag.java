@Entity
@Table(name = "tag")
public class Tag {
    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<EventTag> eventTags;
}