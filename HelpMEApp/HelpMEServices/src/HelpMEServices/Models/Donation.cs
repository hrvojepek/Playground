using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Donation
    {
        public int Id { get; set; }
        public DateTime TimeCreated { get; set; }
        public string Caption { get; set; }
        public string Description { get; set; }
        public string Category { get; set; }
        public string SubCategory { get; set; }
        public string Condition { get; set; }
        public string City { get; set; }

        [ForeignKey("OwnerId")]
        public int OwnerId { get; set; }
        public User Owner { get; set; }

        public List<Photo> Photos { get; set; }

        public List<CommentDonation> Comments { get; set; } 
        
        public List<UserWhoWantDonation> UsersWhoWantDonation { get; set; }      
    }
}
