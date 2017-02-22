using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class UserWhoWantDonation
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }
        public int UserId { get; set; }
        public string UserPhoto { get; set; }

        [ForeignKey("WantedDonationID")]
        public int WantedDonationID { get; set; }
        public Donation WantedDonation { get; set; }
    }
}
