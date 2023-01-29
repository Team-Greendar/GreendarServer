//package greendar.global.auth.oauthlogin.api.auth;
//
//import greendar.global.auth.oauth.domain.UserPrincipal;
//import greendar.global.auth.oauth.token.AuthToken;
//import greendar.global.auth.oauth.token.AuthTokenProvider;
//import greendar.global.auth.oauthlogin.domain.auth.AuthReqModel;
//import greendar.global.common.ApiResponse;
//import greendar.global.config.properties.AppProperties;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
//
//public class AuthApi {
//    private final AppProperties appProperties;
//    private final AuthTokenProvider tokenProvider;
//    private final AuthenticationManager authenticationManager;
//    private final UserRefreshTokenRepository userRefreshTokenRepository;
//
//    private final static long THREE_DAYS_MSEC = 259200000;
//    private final static String REFRESH_TOKEN = "refresh_token";
//
//    @PostMapping("/login")
//    public ApiResponse login(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @RequestBody AuthReqModel authReqModel
//    ) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authReqModel.getId(),
//                        authReqModel.getPassword()
//                )
//        );
//
//        String userId = authReqModel.getId();
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Date now = new Date();
//        AuthToken newAccessToken = tokenProvider.createAuthToken(
//                userId,
//                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
//                new Date(now.getTime()+appProperties.getAuth().getTokenExpiry())
//        );
//
//
//
//
//
//    }
//
//}
