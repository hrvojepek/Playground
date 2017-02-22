using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class CommentEvent : Comment
    {
        [ForeignKey("EventID")]
        public int EventID { get; set; }
        public AppEvent Event { get; set; }
    }
}
