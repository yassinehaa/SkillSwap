
package org.skillswap.skillswapbackend.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skillswap.skillswapbackend.Models.Skill;
import org.skillswap.skillswapbackend.Models.User;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    @JsonIgnoreProperties({"proposedSkills", "searchedSkills", "sentRequests", "receivedRequests"})
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties({"proposedSkills", "searchedSkills", "sentRequests", "receivedRequests"})
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    @JsonIgnoreProperties("user")
    private Skill skill;

    private String status;

    private String paymentMethod;
}
