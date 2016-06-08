using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Message
    {
        public int Id { get; set; }
        public bool Seen { get; set; }
        public string UserSenderName { get; set; }
        public string UserSenderPhoto { get; set; }
        public string MessageText { get; set; }
        public DateTime TimeCreated { get; set; }

        [ForeignKey("ConversationId")]
        public int ConversationId { get; set; }
        public Conversation Conversation { get; set; }
    }
}
