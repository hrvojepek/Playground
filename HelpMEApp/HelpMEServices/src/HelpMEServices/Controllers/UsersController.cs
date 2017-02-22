using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNet.Mvc;
using HelpMEServices.Models;
using Microsoft.Data.Entity;
using System.Text;
using System.IO;
using Microsoft.Extensions.PlatformAbstractions;
using Microsoft.AspNet.Http;
using Microsoft.Net.Http.Headers;
using HelpMEServices.Helper;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace HelpMEServices.Controllers
{
    [Route("api/[controller]")]
    public class UsersController : Controller
    {

        private readonly AppDbContext _context;
        private readonly IApplicationEnvironment _appEnvironment;

        public UsersController(AppDbContext context, IApplicationEnvironment appEnvironment)
        {
            this._context = context;
            this._appEnvironment = appEnvironment;
        }

        // GET: api/users
        [HttpGet]
        public IEnumerable<User> Get()
        {
            return _context.Users
                .Include(e => e.Events)
                .Include(d => d.Donations)
                .Include(w => w.WantedDonations);
        }

        // GET api/users/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var donation = _context.Users
                .Include(d=>d.Donations)
                .Include(e=>e.Events)
                .FirstOrDefault(d => d.Id == id);

            if (donation != null)
            {
                return new ObjectResult(donation);
            }
            else
            {
                var usr = new User
                {
                };

                return new ObjectResult(usr);
            }
        }

        [HttpGet("email/{email}")]
        public IActionResult GetByEmail(string email)
        {
            var user = _context.Users
                .Include(d => d.Donations)
                .Include(e => e.Events)
                .FirstOrDefault(d => d.Email == email);

            if (user != null)
            {
                return new ObjectResult(user);
            }
            else
            {
                var usr = new User
                {
                };

                return new ObjectResult(usr);
            }
        }

        [HttpGet("token/{token}")]
        public IActionResult GetByToken(string token)
        {
            var user = _context.Users
                .Include(d => d.Donations)
                .Include(e => e.Events)
                .FirstOrDefault(d => d.Token == token);

            if (user != null)
            {
                return new ObjectResult(user);
            }
            else
            {
                var usr = new User
                {
                };

                return new ObjectResult(usr);
            }
        }

        // POST api/users
        [HttpPost]
        public IActionResult Post([FromBody]User user)
        {
            if(user!=null && user.Id == 0)
            {
                user.Token = RandomString(15);
                user.UserPhoto = "http://localhost:5000/"+ user.Email + "/profilePhoto.jpg";
                _context.Users.Add(user);
                _context.SaveChanges();

                var path = Path.Combine(_appEnvironment.ApplicationBasePath);
                Directory.CreateDirectory(path + "/wwwroot/" + user.Email);
                Directory.CreateDirectory(path + "/wwwroot/" + user.Email + "/donations");
                Directory.CreateDirectory(path + "/wwwroot/" + user.Email + "/events");

                string userFolder = path + "/wwwroot/" + user.Email + "/profilePhoto.jpg";
                string avatarPhoto = path + "/wwwroot/avatar.jpg";

                System.IO.File.Copy(avatarPhoto, userFolder);

                return new ObjectResult(user);

            }
            else
            {
                var existingUser = _context.Users.FirstOrDefault(u => u.Id == user.Id);
                existingUser.Donations = user.Donations;
                existingUser.Name = user.Name;
                existingUser.SuperUser = user.SuperUser;
                existingUser.Surname = user.Surname;
                existingUser.Phone = user.Phone;
                existingUser.Password = user.Password;
                existingUser.Events = user.Events;
                existingUser.Token = user.Token;
                existingUser.UserPhoto = user.UserPhoto;
                existingUser.Description = user.Description;
                existingUser.Email = user.Email;
                existingUser.Address = user.Address;
                existingUser.City = user.Address;
                return new ObjectResult(existingUser);
            }
        }

        // POST api/users
        [HttpPost("userPhoto/{email}")]
        public IActionResult PostUserPhoto(string email)
        {
            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var pathToUpload = (path + "/wwwroot/" + email + "/");
            var pathToDbSave = "http://localhost:5000/" + email + "/";

            var user = _context.Users.FirstOrDefault(d => d.Email == email);

            var photoName = "";
            
            var files2 = Request.Form.Files;

            foreach (var file in files2)
            {
                if (file.Length > 0)
                {
                    var fileName = ContentDispositionHeaderValue.Parse(file.ContentDisposition).FileName.Trim('"');
                    photoName = fileName;
                    file.SaveAs(Path.Combine(pathToUpload, fileName));
                }
            }
            if (user != null)
            {
                user.UserPhoto = pathToDbSave + photoName;
                _context.SaveChanges();
            }

            return new ObjectResult(user);

        }
        
        
        // PUT api/users/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody]User user)
        {
            var existingUser = _context.Users.FirstOrDefault(u => u.Id == user.Id);

            if (user != null)
            {
                existingUser.Name = user.Name;
                existingUser.SuperUser = user.SuperUser;
                existingUser.Surname = user.Surname;
                existingUser.Phone = user.Phone;
                existingUser.Password = user.Password;
                existingUser.Token = user.Token;
                existingUser.UserPhoto = user.UserPhoto;
                existingUser.Description = user.Description;
                existingUser.Email = user.Email;
                existingUser.Address = user.Address;
                existingUser.City = user.City;

                _context.SaveChanges();
            }
            
            return new ObjectResult(existingUser);
        }

        // DELETE api/users/5
        [HttpDelete("{id}/{email}")]
        public IActionResult Delete(int id, string email)
        {
            var existingUser = _context.Users.FirstOrDefault(u => u.Id == id);
            _context.Users.Remove(existingUser);
            _context.SaveChanges();

            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var userDataPath = (path + "/wwwroot/" + email);

            HelperClass.DeleteDirectory(userDataPath);

            var user = new User { };
            return new ObjectResult(user);
        }


        private static Random random = new Random((int)DateTime.Now.Ticks);
        private string RandomString(int size)
        {
            StringBuilder builder = new StringBuilder();
            char ch;
            for (int i = 0; i < size; i++)
            {
                ch = Convert.ToChar(Convert.ToInt32(Math.Floor(26 * random.NextDouble() + 65)));
                builder.Append(ch);
            }

            return builder.ToString();
        }

    }
}
