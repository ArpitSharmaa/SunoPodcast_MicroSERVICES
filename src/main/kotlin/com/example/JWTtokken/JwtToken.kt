package com.example.JWTtokken



import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

data class TokenConfig(
    val issuer :String,
    val audience :String,
    val expiresIn :Long,
    val secret:String
)

data class tokenclaims(
    val name :String,
    val value :String
)

interface TokenService{
    fun generate (config: TokenConfig,vararg claims:tokenclaims):String
}
class TokengnerateService:TokenService{
    override fun generate(config: TokenConfig, vararg claims: tokenclaims): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis()+config.expiresIn))

        claims.forEach {
            token = token.withClaim(it.name,it.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }

}