package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;//userning takrorlanmas qismi

//    @Size(min = 3,max = 50)  Regiter Dto da Bu anatatsiyalar yozildi
    @Column(nullable = false,length = 50)
    private String firstName;//ismi

//    @Length(min = 3,max = 50) Regiter Dto da Bu anatatsiyalar yozildi
    @Column(nullable = false)
    private String lastName;//familyasi


//    @Email Regiter Dto da Bu anatatsiyalar yozildi
    @Column(unique = true,nullable = false)
    private String email;//userning emaili(USERNAME SIFATIDA)



    private String password;//kalit so'zi


    @CreationTimestamp
    private Timestamp createdAt;//qachon ro'yxatdan o'tganligi

    @UpdateTimestamp
    private Timestamp updateAt;//oxirgi marta qachon tahrirlanganligi

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean accountNonExpired = true;// bu userning amal qilish muddati otmaganligi

    private boolean accountNonLocked = true;// bu user bloklanmaganligi

    private boolean credentialsNonExpired = true;

    private boolean enabled;

    private String emailCode;

    //------BU USER DETAILS METHODLARI-----//////


    //BU USERNING HUQUQLARI RO'YXATI
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    //USERNING USERNAMENI QAYTARUVCHI METHOD
    public String getUsername() {
        return this.email;
    }


    //ACCOUNTNING AMAL QILISH MUDDATINI QAYTARI
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //ACCOUNT BLOCKLANGANLIGI HOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    //ACCOUNTNING ISHONCHILILIK MUDDATI TUGAGAN YOKI TUGAMAGANLIGINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    //ACCOUNTNING YONIQ YOKI O'CHIQLIGINI QAYTARADI
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
