package com.ptm.api.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * A user.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@NotNull
	@Column(name = "parent_id")
	private String parentId;
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "user_name", length = 50, unique = true, nullable = false)
	private String username;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60, nullable = false)
	private String password;

	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Size(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	@Size(max = 50)
	@Column(name = "phone_number", length = 12)
	private String phonenumber;

	@Email
	@Size(min = 5, max = 254)
	@Column(length = 254, unique = true)
	private String email;
	
	@Column(name = "password_reset_date")
	private LocalDateTime resetDate ;
	
	@Column(name = "reset_key")
	private String resetKey ;

	@Size(min = 2, max = 6)
	@Column(name = "lang_key", length = 6)
	private String langKey;

	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	@JsonIgnore
	private String activationKey;


	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles = new HashSet<>();
	
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_api_mapping", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "api_id", referencedColumnName = "id") })
	@BatchSize(size = 20)
	private Collection<PtmApi> userApi = new HashSet<>();
	
	@Column(name = "uuid")
	private String uuid;
		
	@Column(name = "user_source")
	private String userSource;
	
	
	@Size(min= 10, max = 10)
	@Column(name = "dob", length = 50)
	private String dob;

	@Size(min = 5, max = 254)
	@Column(name= "address_1",length = 254)
	private String address1;
	
	@Column(name= "address_2")
	private String address2;
	
	@Size(min = 6, max = 6)
	@Column(name= "pincode",length = 6)
	private String pincode;
	
	@Size(max = 254)
	@Column(name= "landmark",length = 254)
	private String landmark;
	
	
	@Column(name= "user_commission_uuid")
	private String userComissionUuid;
	
	@Column(name= "api_key")
	private String apiKey;
	
	@Column(name= "qr_code_id")
	private String qrCodeId;
	
	@Column(name= "ip_address")
	private String ipAddress;
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;
		return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
