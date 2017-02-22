using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Photo
    {
        public int Id { get; set; }
        public string PhotoPath { get; set; }

        [ForeignKey("DonationId")]
        public int DonationId { get; set; }
        public Donation donation { get; set; }
    }
}
