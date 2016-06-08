using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class AppEvent
    {
        public int Id { get; set; }

        [ForeignKey("UserOwnerId")]
        public int UserOwnerId { get; set; }
        public User UserOwner { get; set; }
        public string Caption { get; set; }
        public string Description { get; set; }
        public string Photo { get; set; }
        public DateTime StartingTime { get; set; }
        public DateTime EndingTime { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public List<CommentEvent> Comments { get; set; }

        public List<UserWhoAttending> UsersWhoAttending { get; set; }
    }
}
