using Microsoft.Data.Entity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class AppDbContext : DbContext
    {
        public DbSet<Photo> Photos { get; set; }
        public DbSet<CommentDonation> DonationComments { get; set; }
        public DbSet<Donation> Donations { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Conversation> Conversations { get; set; }
        public DbSet<Message> Messages { get; set; }
        public DbSet<AppEvent> HumanitaryEvents { get; set; }
        public DbSet<CommentEvent> EventComments { get; set; }
        public DbSet<Feedback> Feedbacks { get; set; }
        public DbSet<WantedDonation> WantedDonations { get; set; }
        public DbSet<AppEventAttending> HumanitaryEventAttendings { get; set; }
        public DbSet<UserWhoAttending> UsersWhoAttending { get; set; }
        public DbSet<UserWhoWantDonation> UsersWhoWantDonation { get; set; }


        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(
                @"Server=(localdb)\ProjectsV13;Database=HelpMEDBTesting2;Trusted_Connection=true;MultipleActiveResultSets=true;");
            base.OnConfiguring(optionsBuilder);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {                 
        }

    }
}
