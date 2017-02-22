using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class User
    {
        public int Id { get; set; }
        public bool SuperUser { get; set; }
        public string Token { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string Phone { get; set; }
        public string Description { get; set; }
        public string UserPhoto { get; set; }
        public string Address { get; set; }
        public string City { get; set; }

        public List<Donation> Donations { get; set; }
        public List<AppEvent> Events { get; set; }
        //public List<AppEventAttending> EventsAttending { get; set; }
        public List<WantedDonation> WantedDonations { get; set; }

    }
}
