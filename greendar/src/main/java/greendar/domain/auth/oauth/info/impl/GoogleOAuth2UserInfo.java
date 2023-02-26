package greendar.domain.auth.oauth.info.impl;

import greendar.domain.auth.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    //변경해야 할수도 있음
    public String getPassword(){
        return (String) attributes.get("password");
    }
    //변경해야 할수도 있음
    public String getMessage(){
        return (String) attributes.get("message");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
