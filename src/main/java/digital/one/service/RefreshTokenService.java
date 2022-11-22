package digital.one.service;

import digital.one.dto.request.RefreshTokenRequest;
import digital.one.entity.auth.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    void validationToken(String refreshToken);

    void refreshTokenDelete(RefreshTokenRequest request);
}
