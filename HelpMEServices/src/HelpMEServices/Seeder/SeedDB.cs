using HelpMEServices.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Seeder
{
    public class SeedDB
    {
        private readonly AppDbContext _context;

        public SeedDB(AppDbContext context)
        {
            _context = context;
        }

        public async Task SeedData()
        {
            if (!_context.Users.Any())
            {
                var users = new List<User>
            {
                new User
                {
                    SuperUser = true,
                    Token = RandomString(15),
                    Name = "Hrvoje",
                    Surname = "Pek",
                    Email = "hrvoje.pek1@gmail.com",
                    Password = "pass",
                    Phone = "+385 99 744 9277",
                    Description = "Hi, I am owner of this web application. Welcome!",
                    UserPhoto = "https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-0/p280x280/13221736_539557389565522_1756510862887361463_n.jpg?oh=0da24bb22ce6b817cffdbe12efc85c0e&oe=57CB5FF4",

                    Donations = new List<Donation>
                    {
                        new Donation
                        {
                            TimeCreated = DateTime.Now,
                            Caption = "Blue T-Shirt",
                            Description = "This T-Shirt is to small for me so I want to give it someone",
                            Category = "Chlotes",
                            SubCategory = "Men",
                            Condition = "Good",
                            OwnerId = 1,
                            Photos = new List<Photo>
                            {
                                new Photo
                                {
                                    PhotoPath = "http://localhost:5000/hrvoje.pek1@gmail.com/donations/1/t-shirt1.jpg",
                                    DonationId = 1
                                },
                                new Photo
                                {
                                    PhotoPath = "http://localhost:5000/hrvoje.pek1@gmail.com/donations/1/tshirt2.jpg",
                                    DonationId = 1
                                }
                            },
                            Comments = new List<CommentDonation>
                            {

                            },
                            UsersWhoWantDonation = new List<UserWhoWantDonation>
                            {

                            },
                            City = "Zagreb"
                        }
                    },
                    Events = new List<AppEvent>
                    {
                        new AppEvent
                        {
                            UserOwnerId = 1,
                            Caption = "Wings for Life",
                            Description = "This event is for everyone who wants to run for those who can't. All money is going for poor children without parents.",
                            Photo = "http://localhost:5000/hrvoje.pek1@gmail.com/events/1/hum1.jpg",
                            StartingTime = DateTime.Now,
                            EndingTime = DateTime.Now,
                            Address = "Ilica 1",
                            City = "Zagreb",
                            UsersWhoAttending = new List<UserWhoAttending>
                            {
                                new UserWhoAttending
                                {
                                    Name = "Alexis",
                                    Surname = "Sanchez",
                                    UserPhoto = "http://localhost:5000/alexis@gmail.com/alexis.jpg",
                                    UserId = 2,
                                    EventAttendingID = 1                                    
                                }
                            }
                        }
                    },
                    EventsAttending = new List<AppEventAttending>
                    {

                    },
                    WantedDonations = new List<WantedDonation>
                    {

                    },
                    City = "Zagreb",
                    Address = "Zagorska 46"

                },
                new User
                {
                    SuperUser = false,
                    Token = RandomString(15),
                    Name = "Alexis",
                    Surname = "Sanchez",
                    Email = "alexis@mail.com",
                    Password = "pass",
                    Phone = "+488 99 722 9988",
                    Description = "Very positive person who likes to help people. See my donations",
                    UserPhoto = "http://localhost:5000/alexis@gmail.com/alexis.jpg",

                    Donations = new List<Donation>
                    {
                        new Donation
                        {
                            TimeCreated = DateTime.Now,
                            Caption = "Samsung Mobile Phone",
                            Description = "Very good Samsung Mobile phone S7",
                            Category = "Technology",
                            SubCategory = "Mobiles",
                            Condition = "Good",
                            OwnerId = 2,
                            Photos = new List<Photo>
                            {
                                new Photo
                                {
                                    PhotoPath = "http://localhost:5000/alexis@gmail.com/donations/2/sams1.jpg",
                                    DonationId = 2
                                },
                                new Photo
                                {
                                    PhotoPath = "http://localhost:3000/uploads/alexis@gmail.com/donations/2/sams2.jpg",
                                    DonationId = 2
                                }
                            },
                            Comments = new List<CommentDonation>
                            {

                            },
                            UsersWhoWantDonation = new List<UserWhoWantDonation>
                            {

                            },
                            City = "Zaprešić"
                        }
                    },
                    Events = new List<AppEvent>
                    {

                    },
                    EventsAttending = new List<AppEventAttending>
                    {

                    },
                    WantedDonations = new List<WantedDonation>
                    {

                    },
                    City = "Zaprešić",
                    Address = "Ulica Stjepana Radića 99"

                }
            };

                _context.Users.AddRange(users);
                await _context.SaveChangesAsync();
            }
            if (!_context.Feedbacks.Any())
            {

                var feedbacks = new List<Feedback>
                {
                    new Feedback
                    {
                        UserPhoto = "https://upload.wikimedia.org/wikipedia/commons/0/0b/TigerWoodsOct2011.jpg",
                        UserName = "Wider Toods",
                        FeedbackComment = "This website is very useful, I am happy to come here and help people and also I don't need to read sh*t about Big Brother and other realities."
                    },
                    new Feedback
                    {
                        UserPhoto = "http://www.ew.com/sites/default/files/1447796548/david-beckham-02.jpg",
                        UserName = "Bavid Deckham",
                        FeedbackComment = "Wonderful web application where I can help other people."
                    },
                    new Feedback
                    {
                        UserPhoto = "http://images.shape.mdpcdn.com/sites/shape.com/files/styles/story_detail/public/story/charlize-theron-rotator_0.jpg?itok=vxWGaJRn",
                        UserName = "Charla Therona",
                        FeedbackComment = "Amazing website and very easy to use."
                    }
                };

                _context.Feedbacks.AddRange(feedbacks);
                await _context.SaveChangesAsync();
            }

            if (!_context.Conversations.Any())
            {
                var conversations = new List<Conversation>
                {
                    new Conversation
                    {
                        User1Id = 1,
                        User2Id = 2,
                        Messages = new List<Message>
                        {
                            new Message
                            {
                                Seen = true,
                                UserSenderName = "Hrvoje",
                                UserSenderPhoto = "https://scontent-mxp1-1.xx.fbcdn.net/v/t1.0-0/p280x280/13221736_539557389565522_1756510862887361463_n.jpg?oh=0da24bb22ce6b817cffdbe12efc85c0e&oe=57CB5FF4",
                                MessageText = "Test message",
                                TimeCreated = DateTime.Now
                            },
                            new Message
                            {
                                Seen = false,
                                UserSenderName = "Alexis",
                                UserSenderPhoto = "http://localhost:5000/alexis@gmail.com/alexis.jpg",
                                MessageText = "Test message reply",
                                TimeCreated = DateTime.Now.AddMinutes(2)
                            }
                        }
                    }
                };

                _context.Conversations.AddRange(conversations);
                await _context.SaveChangesAsync();
            }
        }

        private string RandomString(int length)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            var random = new Random();
            return new string(Enumerable.Repeat(chars, length)
              .Select(s => s[random.Next(s.Length)]).ToArray());
        }

    }
}
