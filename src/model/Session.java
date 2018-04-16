/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import repositories.Repository;

/**
 *
 * @author Ebenezer Graham
 */
public class Session {
    
    protected final int id;
    protected final String name;
    protected final String duration;
    protected final Repository attendees;
    protected final String facilitator;

    public Session(int id, String name, String duration, Repository attendees, String facilitator) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.attendees = attendees;
        this.facilitator = facilitator;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public Repository getAttendees() {
        return attendees;
    }

    public String getFacilitator() {
        return facilitator;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.duration);
        hash = 67 * hash + Objects.hashCode(this.attendees);
        hash = 67 * hash + Objects.hashCode(this.facilitator);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Session other = (Session) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        if (!Objects.equals(this.facilitator, other.facilitator)) {
            return false;
        }
        if (!Objects.equals(this.attendees, other.attendees)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", name=" + name + ", duration=" + duration + ", attendees=" + attendees + ", facilitator=" + facilitator + '}';
    }
    
    
}
