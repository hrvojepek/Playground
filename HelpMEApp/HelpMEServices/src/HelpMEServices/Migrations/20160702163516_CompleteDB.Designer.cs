using System;
using Microsoft.Data.Entity;
using Microsoft.Data.Entity.Infrastructure;
using Microsoft.Data.Entity.Metadata;
using Microsoft.Data.Entity.Migrations;
using HelpMEServices.Models;

namespace HelpMEServices.Migrations
{
    [DbContext(typeof(AppDbContext))]
    [Migration("20160702163516_CompleteDB")]
    partial class CompleteDB
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.0-rc1-16348")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("HelpMEServices.Models.AppEvent", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Address");

                    b.Property<string>("Caption");

                    b.Property<string>("City");

                    b.Property<string>("Description");

                    b.Property<DateTime>("EndingTime");

                    b.Property<string>("Photo");

                    b.Property<DateTime>("StartingTime");

                    b.Property<int>("UserOwnerId");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.AppEventAttending", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Caption");

                    b.Property<string>("Description");

                    b.Property<int>("EventID");

                    b.Property<string>("Photo");

                    b.Property<DateTime>("TimeEnd");

                    b.Property<DateTime>("TimeStart");

                    b.Property<string>("UserNameSurnameOwner");

                    b.Property<int>("UserWhoAttendingId");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.CommentDonation", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("CommentTxt");

                    b.Property<int>("DonationId");

                    b.Property<DateTime>("TimeCreated");

                    b.Property<string>("UserName");

                    b.Property<string>("UserPhoto");

                    b.Property<string>("UserSurname");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.CommentEvent", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int?>("AppEventAttendingId");

                    b.Property<string>("CommentTxt");

                    b.Property<int>("EventID");

                    b.Property<DateTime>("TimeCreated");

                    b.Property<string>("UserName");

                    b.Property<string>("UserPhoto");

                    b.Property<string>("UserSurname");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.Conversation", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("User1Id");

                    b.Property<int>("User2Id");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.Donation", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Caption");

                    b.Property<string>("Category");

                    b.Property<string>("City");

                    b.Property<string>("Condition");

                    b.Property<string>("Description");

                    b.Property<int>("OwnerId");

                    b.Property<string>("SubCategory");

                    b.Property<DateTime>("TimeCreated");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.Feedback", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("FeedbackComment");

                    b.Property<string>("UserName");

                    b.Property<string>("UserPhoto");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.Message", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("ConversationId");

                    b.Property<string>("MessageText");

                    b.Property<bool>("Seen");

                    b.Property<DateTime>("TimeCreated");

                    b.Property<string>("UserSenderName");

                    b.Property<string>("UserSenderPhoto");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.Photo", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("DonationId");

                    b.Property<string>("PhotoPath");

                    b.Property<int?>("WantedDonationId");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Address");

                    b.Property<string>("City");

                    b.Property<string>("Description");

                    b.Property<string>("Email");

                    b.Property<string>("Name");

                    b.Property<string>("Password");

                    b.Property<string>("Phone");

                    b.Property<bool>("SuperUser");

                    b.Property<string>("Surname");

                    b.Property<string>("Token");

                    b.Property<string>("UserPhoto");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.UserWhoAttending", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("AppEventId");

                    b.Property<string>("Name");

                    b.Property<string>("Surname");

                    b.Property<int>("UserId");

                    b.Property<string>("UserPhoto");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.UserWhoWantDonation", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Name");

                    b.Property<string>("Surname");

                    b.Property<int>("UserId");

                    b.Property<string>("UserPhoto");

                    b.Property<int>("WantedDonationID");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.WantedDonation", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Caption");

                    b.Property<string>("Category");

                    b.Property<int?>("CommentsId");

                    b.Property<string>("Condition");

                    b.Property<string>("Description");

                    b.Property<int>("DonationId");

                    b.Property<string>("SubCategory");

                    b.Property<string>("UserOwnerNameSurname");

                    b.Property<int>("UserWhoWantDonationID");

                    b.HasKey("Id");
                });

            modelBuilder.Entity("HelpMEServices.Models.AppEvent", b =>
                {
                    b.HasOne("HelpMEServices.Models.User")
                        .WithMany()
                        .HasForeignKey("UserOwnerId");
                });

            modelBuilder.Entity("HelpMEServices.Models.AppEventAttending", b =>
                {
                    b.HasOne("HelpMEServices.Models.User")
                        .WithMany()
                        .HasForeignKey("UserWhoAttendingId");
                });

            modelBuilder.Entity("HelpMEServices.Models.CommentDonation", b =>
                {
                    b.HasOne("HelpMEServices.Models.Donation")
                        .WithMany()
                        .HasForeignKey("DonationId");
                });

            modelBuilder.Entity("HelpMEServices.Models.CommentEvent", b =>
                {
                    b.HasOne("HelpMEServices.Models.AppEventAttending")
                        .WithMany()
                        .HasForeignKey("AppEventAttendingId");

                    b.HasOne("HelpMEServices.Models.AppEvent")
                        .WithMany()
                        .HasForeignKey("EventID");
                });

            modelBuilder.Entity("HelpMEServices.Models.Donation", b =>
                {
                    b.HasOne("HelpMEServices.Models.User")
                        .WithMany()
                        .HasForeignKey("OwnerId");
                });

            modelBuilder.Entity("HelpMEServices.Models.Message", b =>
                {
                    b.HasOne("HelpMEServices.Models.Conversation")
                        .WithMany()
                        .HasForeignKey("ConversationId");
                });

            modelBuilder.Entity("HelpMEServices.Models.Photo", b =>
                {
                    b.HasOne("HelpMEServices.Models.Donation")
                        .WithMany()
                        .HasForeignKey("DonationId");

                    b.HasOne("HelpMEServices.Models.WantedDonation")
                        .WithMany()
                        .HasForeignKey("WantedDonationId");
                });

            modelBuilder.Entity("HelpMEServices.Models.UserWhoAttending", b =>
                {
                    b.HasOne("HelpMEServices.Models.AppEvent")
                        .WithMany()
                        .HasForeignKey("AppEventId");
                });

            modelBuilder.Entity("HelpMEServices.Models.UserWhoWantDonation", b =>
                {
                    b.HasOne("HelpMEServices.Models.Donation")
                        .WithMany()
                        .HasForeignKey("WantedDonationID");
                });

            modelBuilder.Entity("HelpMEServices.Models.WantedDonation", b =>
                {
                    b.HasOne("HelpMEServices.Models.CommentDonation")
                        .WithMany()
                        .HasForeignKey("CommentsId");

                    b.HasOne("HelpMEServices.Models.User")
                        .WithMany()
                        .HasForeignKey("UserWhoWantDonationID");
                });
        }
    }
}
