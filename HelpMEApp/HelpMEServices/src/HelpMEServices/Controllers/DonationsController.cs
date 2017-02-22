using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNet.Mvc;
using HelpMEServices.Models;
using Microsoft.Data.Entity;
using Microsoft.Extensions.PlatformAbstractions;
using System.IO;
using Microsoft.Net.Http.Headers;
using Microsoft.AspNet.Http;
using HelpMEServices.Helper;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace HelpMEServices.Controllers
{
    [Route("api/[controller]")]
    public class DonationsController : Controller
    {

        private readonly AppDbContext _context;
        private readonly IApplicationEnvironment _appEnvironment;

        public DonationsController(AppDbContext context, IApplicationEnvironment appEnvironment)
        {
            this._context = context;
            this._appEnvironment = appEnvironment;
        }

        // GET: api/donations
        [HttpGet]
        public IEnumerable<Donation> Get()
        {
            return _context.Donations
                .Include(u => u.Owner)
                .Include(c => c.Comments)
                .Include(p => p.Photos)
                .Include(w=>w.UsersWhoWantDonation);
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var donation = _context.Donations
                .Include(u => u.Owner)
                .Include(p => p.Photos)
                .Include(w => w.UsersWhoWantDonation)
                .FirstOrDefault(d => d.Id == id);

            if (donation != null)
            {
                return new ObjectResult(donation);
            }
            else
            {
                return new HttpNotFoundResult();
            }
        }

        [HttpGet("owner/{ownerId}")]
        public IEnumerable<Donation> GetDonationsByOwner(int ownerId)
        {
            return _context.Donations
               .Include(c => c.Comments)
               .Include(p => p.Photos).Where(d => d.OwnerId == ownerId);
            
        }

        [HttpPost]
        public IActionResult PostDonation([FromBody]Donation donation)
        {
            if (donation != null && donation.Id == 0)
            {
                donation.TimeCreated = DateTime.Now;
                _context.Donations.Add(donation);
                _context.SaveChanges();
                return new ObjectResult(donation);
            }
            else
            {
                var noDonation = new Donation { };
                return new ObjectResult(noDonation);
            }
        }

        // POST api/donations/email
        [HttpPost("donationsPhotos/{email}/{donationID}")]
        public IActionResult PostDonationPhoto(string email, string donationId)
        {
            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var pathOfDirectory = (path + "/wwwroot/" + email + "/donations/" + donationId);

            if (!Directory.Exists(pathOfDirectory))
            {
                Directory.CreateDirectory(pathOfDirectory);
            }
            
            var pathToUpload = (path + "/wwwroot/" + email + "/donations/" + donationId + "/");
            var pathToDbSave = "http://localhost:5000/" + email + "/donations/" + donationId + "/";
            

            var photoName = "";
            var donId = Int32.Parse(donationId);

            var files = Request.Form.Files;

            foreach (var file in files)
            {
                if (file.Length > 0)
                {
                    var fileName = ContentDispositionHeaderValue.Parse(file.ContentDisposition).FileName.Trim('"');
                    photoName = fileName;
                    file.SaveAs(Path.Combine(pathToUpload, fileName));

                    var photo = new Photo
                    {
                        DonationId = donId,
                        PhotoPath = pathToDbSave + fileName                        
                    };
                    if (photo != null)
                    {
                        _context.Photos.Add(photo);
                        _context.SaveChanges();
                    }
                }
            }
            
            return new ObjectResult(200);

        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody]Donation donation)
        {
            var existingDonation = _context.Donations.FirstOrDefault(u => u.Id == donation.Id);

            if (donation != null)
            {
                existingDonation.Id = donation.Id;
                existingDonation.TimeCreated = DateTime.Now;
                existingDonation.OwnerId = donation.OwnerId;
                existingDonation.Caption = donation.Caption;
                existingDonation.Category = donation.Category;
                existingDonation.City = donation.City;
                existingDonation.Condition = donation.Condition;
                existingDonation.Description = donation.Description;
                existingDonation.Photos = donation.Photos;
                existingDonation.SubCategory = donation.SubCategory;

                _context.SaveChanges();
            }

            return new ObjectResult(existingDonation);
        }

        // DELETE api/values/5
        [HttpDelete("{id}/{email}")]
        public IActionResult Delete(int id, string email)
        {
            var donation = _context.Donations.FirstOrDefault(v => v.Id == id);
            _context.Donations.Remove(donation);
            _context.SaveChanges();

            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var donationDataPath = (path + "/wwwroot/" + email + "/donations/" + id);

            HelperClass.DeleteDirectory(donationDataPath);

            var d = new Donation { };
            return new ObjectResult(d);
        }
    }
}
