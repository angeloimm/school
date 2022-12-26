package it.olegna.schoolmgmt.domain;

import it.olegna.schoolmgmt.enums.TipoUtenteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DiomedeeUserDetail implements UserDetails {
    private UUID idUtente;
    private String username;
    // Indica l'ID generato da WSO2 e restituito dall'oggetto Resource quando salvo
    // in WSO2
    private String uniqueId;
    @ToString.Exclude
    private String password;
    //Anagrafica
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String codiceFiscale;
    private String sesso;
    private String telefono;
    // Non mettiamo vincolo di univocità di proposito. Esperienze dimostrano che la
    // mail è scambiata tra utenti
    private String mail;
    // Nel caso di medico bisogna indicare il codice regionale
    private String codiceRegionale;
    // data abilitazione dell'utente
    private Date dataAbilitazione;
    //data disabilitazione dell'utente
    private Date dataDisabilitazione;

    private Boolean enabled;
    private Boolean amministratore;
    private List<TipoUtenteEnum> tipoUtente;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (tipoUtente == null || tipoUtente.isEmpty()) {
            return Collections.emptyList();
        }
        List<GrantedAuthority> result = new ArrayList<>();
        this.tipoUtente.stream().forEach(ruolo -> result.add(new SimpleGrantedAuthority(ruolo.name())));
        return result;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //Se enabled true --> utente OK
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        //Se enabled true --> utente OK
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //Se enabled true --> utente OK
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        //Se enabled true --> utente OK
        return this.enabled;
    }
}
