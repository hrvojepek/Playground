using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class UserWhoAttending
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public string Name { get; set; }
        public string Surname { get; set; }
        public string UserPhoto { get; set; }

        [ForeignKey("EventAttendingID")]
        public int EventAttendingID { get; set; }
        public AppEvent AppEvent { get; set; }
        
    }
}
