@Entity
@Table(name = "organizer_with_token")
public class OrganizerWithToken extends Organizer {
    private String token;
}