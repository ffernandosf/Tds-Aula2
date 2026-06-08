package br.edu.ifsul.cstsi.projetotds.autenticacao;

import br.edu.ifsul.cstsi.projetotds.infra.security.TokenJwtDTO;
import br.edu.ifsul.cstsi.projetotds.infra.security.TokenService;
import br.edu.ifsul.cstsi.projetotds.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJwtDTO> efetuaLogin(@RequestBody UsuarioAutenticacaoDTO data) {
        var authenticationDTO = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var authentication = manager.authenticate(authenticationDTO);
        var tokenJWT = tokenService.geraToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}
