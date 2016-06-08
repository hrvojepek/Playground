using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class AppEventAttending
    {
        public int Id { get; set; }
        public int EventID { get; set; }
        public string Caption { get; set; }
        public string Description { get; set; }
        public string Photo { get; set; }
        public DateTime TimeStart { get; set; }
        public DateTime TimeEnd { get; set; }
        public string UserNameSurnameOwner { get; set; }
        public List<CommentEvent> Comments { get; set; }

        [ForeignKey("UserWhoAttendingId")]
        public int UserWhoAttendingId { get; set; }
        public User UserWhoAttending { get; set; }
    }
}
