using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Comment
    {
        public int Id { get; set; }

        [ForeignKey("UserOwnerID")]
        public int UserOwnerId { get; set; }
        public User User { get; set; }
        public string CommentTxt { get; set; }
        public DateTime TimeCreated { get; set; }
    }
}
