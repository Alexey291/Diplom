package main.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.api.response.marker.Response;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponse implements Response {
    public String name;
    public String email;
    public String password;
    public String photo;
    public String removePhoto;
}
