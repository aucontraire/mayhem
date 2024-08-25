package com.mm.mayhem.model.db.users;

import com.mm.mayhem.model.db.BaseModel;
import com.mm.mayhem.model.db.geo.City;
import com.mm.mayhem.model.db.geo.Country;
import com.mm.mayhem.model.db.geo.StateRegion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "id", nullable = true, unique = true)
    protected Long id;
    @Column(name = "created")
    protected ZonedDateTime created;
    @Column(name = "updated")
    protected ZonedDateTime updated;
    @Column(name = "active")
    protected Boolean active;
    @Column(name = "joined")
    protected LocalDate joined;
    @Column(name = "last_activity")
    protected LocalDate lastActivity;
    @Column(name = "username")
    protected String username;
    @Column(name = "user_number")
    protected Integer userNumber;
    @Column(name = "profile_url")
    protected URL profileUrl;
    @Column(name = "profile_url_readable")
    protected URL profileUrlReadable;
    @Column(name = "age")
    protected Integer age;
    @Column(name = "birth_date")
    protected LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    protected Country country;

    @ManyToOne
    @JoinColumn(name = "state_region_id")
    protected StateRegion stateRegion;

    @ManyToOne
    @JoinColumn(name = "city_id")
    protected City city;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "profile_details", columnDefinition = "jsonb")
    protected String profileDetails;
    @Email(message = "Email should be valid")
    @Size(max = 255)
    @Column(name = "email", nullable = false, unique = true, length = 255)
    protected String email;
    @Column(name = "twitter")
    protected URL twitter;
    @Column(name = "notes", columnDefinition = "TEXT", nullable = true)
    protected String notes;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ZonedDateTime getCreated() {
        return created;
    }

    @Override
    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @Override
    public ZonedDateTime getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public void setJoined(LocalDate joined) {
        this.joined = joined;
    }

    public LocalDate getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDate lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public URL getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(URL profileUrl) {
        this.profileUrl = profileUrl;
    }

    public URL getProfileUrlReadable() {
        return profileUrlReadable;
    }

    public void setProfileUrlReadable(URL profileUrlReadable) {
        this.profileUrlReadable = profileUrlReadable;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public StateRegion getStateRegion() {
        return stateRegion;
    }

    public void setStateRegion(StateRegion stateRegion) {
        this.stateRegion = stateRegion;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(String profileDetails) {
        this.profileDetails = profileDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public URL getTwitter() {
        return twitter;
    }

    public void setTwitter(URL twitter) {
        this.twitter = twitter;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
