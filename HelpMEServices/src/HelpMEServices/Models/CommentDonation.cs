using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class CommentDonation : Comment
    {
        [ForeignKey("DonationID")]
        public int DonationId { get; set; }
        public Donation Donation { get; set; }
    }
}
