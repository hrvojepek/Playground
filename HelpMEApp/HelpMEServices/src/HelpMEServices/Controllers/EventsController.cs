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
    public class EventsController : Controller
    {
        private readonly AppDbContext _context;
        private readonly IApplicationEnvironment _appEnvironment;

        public EventsController(AppDbContext context, IApplicationEnvironment appEnvironment)
        {
            this._context = context;
            this._appEnvironment = appEnvironment;
        }

        // GET: api/events
        [HttpGet]
        public IEnumerable<AppEvent> Get()
        {
            return _context.HumanitaryEvents
                .Include(u => u.UserOwner)
                .Include(c => c.Comments)
                .Include(s=>s.UsersWhoAttending);
        }

        // GET api/events/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var humEvent = _context.HumanitaryEvents
                .Include(u => u.UserOwner)
                .Include(a => a.UsersWhoAttending)
                .FirstOrDefault(d => d.Id == id);

            if (humEvent != null)
            {
                return new ObjectResult(humEvent);
            }
            else
            {
                return new HttpNotFoundResult();
            }
        }

        [HttpGet("owner/{ownerId}")]
        public IEnumerable<AppEvent> GetEventsByOwner(int ownerId)
        {
            return _context.HumanitaryEvents
               .Include(c => c.Comments)
               .Include(u=> u.UserOwner)
               .Include(a=> a.UsersWhoAttending)
               .Where(d => d.UserOwnerId == ownerId);

        }

        [HttpPost]
        public IActionResult PostEvent([FromBody]AppEvent appEvent)
        {
            if (appEvent != null && appEvent.Id == 0)
            {
                appEvent.StartingTime = appEvent.StartingTime.AddHours(-2);
                appEvent.EndingTime = appEvent.EndingTime.AddHours(-2);
                _context.HumanitaryEvents.Add(appEvent);
                _context.SaveChanges();
                return new ObjectResult(appEvent);
            }
            else
            {
                var noDonation = new Donation { };
                return new ObjectResult(noDonation);
            }
        }

        // POST api/events/email
        [HttpPost("eventsPhotos/{email}/{eventID}")]
        public IActionResult PostEventPhoto(string email, string eventID)
        {
            var eventId = Int32.Parse(eventID);
            var humEvent = _context.HumanitaryEvents.FirstOrDefault(e => e.Id == eventId);

            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var pathOfDirectory = (path + "/wwwroot/" + email + "/events/" + eventID);

            if (!Directory.Exists(pathOfDirectory))
            {
                Directory.CreateDirectory(pathOfDirectory);
            }

            var pathToUpload = (path + "/wwwroot/" + email + "/events/" + eventID + "/");
            var pathToDbSave = "http://localhost:5000/" + email + "/events/" + eventID + "/";


            var photoName = "";

            var files = Request.Form.Files;

            foreach (var file in files)
            {
                if (file.Length > 0)
                {
                    var fileName = ContentDispositionHeaderValue.Parse(file.ContentDisposition).FileName.Trim('"');
                    photoName = fileName;
                    file.SaveAs(Path.Combine(pathToUpload, fileName));
                    photoName = pathToDbSave + fileName;

                }
            }
            humEvent.Photo = photoName;
            _context.SaveChanges();
            return new ObjectResult(200);

        }

        // PUT api/events/5
        [HttpPut("{id}")]
        public IActionResult Put(int id, [FromBody]AppEvent appEvent)
        {
            var existingEvent = _context.HumanitaryEvents.FirstOrDefault(e=>e.Id == id);

            if (appEvent != null)
            {
                existingEvent.Address = appEvent.Address;
                existingEvent.Caption = appEvent.Caption;
                existingEvent.City = appEvent.City;
                existingEvent.Comments = appEvent.Comments;
                existingEvent.Description = appEvent.Description;
                existingEvent.EndingTime = appEvent.EndingTime;
                existingEvent.Photo = appEvent.Photo;
                existingEvent.StartingTime = appEvent.StartingTime;
                existingEvent.UserOwner = appEvent.UserOwner;
                existingEvent.UserOwnerId = appEvent.UserOwnerId;
                existingEvent.UsersWhoAttending = appEvent.UsersWhoAttending;
                _context.SaveChanges();
            }

            return new ObjectResult(existingEvent);
        }

        // DELETE api/values/5
        [HttpDelete("{id}/{email}")]
        public IActionResult Delete(int id, string email)
        {
            var appevent = _context.HumanitaryEvents.FirstOrDefault(d => d.Id == id);
            if (appevent != null)
            {
                _context.HumanitaryEvents.Remove(appevent);
                _context.SaveChanges();

            }

            var path = Path.Combine(_appEnvironment.ApplicationBasePath);
            var eventnDataPath = (path + "/wwwroot/" + email + "/events/" + id);

            HelperClass.DeleteDirectory(eventnDataPath);

            var e = new AppEvent { };
            return new ObjectResult(e);
        }
    }
}
