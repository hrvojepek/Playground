using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class WantedDonation
    {
        public int Id { get; set; }
        public int DonationId { get; set; }
        public string Caption { get; set; }
        public string Description { get; set; }
        public string Category { get; set; }
        public string SubCategory { get; set; }
        public string Condition { get; set; }

        public string UserOwnerNameSurname { get; set; }

        [ForeignKey("UserWhoWantDonationID")]
        public int UserWhoWantDonationID { get; set; }
        public User UserWhoWantDonation { get; set; }

        public List<Photo> Photos { get; set; }

        public CommentDonation Comments { get; set; }
        
        
    }
}
