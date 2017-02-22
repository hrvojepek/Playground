using System;
using System.Collections.Generic;
using Microsoft.Data.Entity.Migrations;

namespace HelpMEServices.Migrations
{
    public partial class CompletedDb2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(name: "FK_AppEvent_User_UserOwnerId", table: "AppEvent");
            migrationBuilder.DropForeignKey(name: "FK_AppEventAttending_User_UserWhoAttendingId", table: "AppEventAttending");
            migrationBuilder.DropForeignKey(name: "FK_CommentDonation_Donation_DonationId", table: "CommentDonation");
            migrationBuilder.DropForeignKey(name: "FK_CommentEvent_AppEventAttending_AppEventAttendingId", table: "CommentEvent");
            migrationBuilder.DropForeignKey(name: "FK_CommentEvent_AppEvent_EventID", table: "CommentEvent");
            migrationBuilder.DropForeignKey(name: "FK_Donation_User_OwnerId", table: "Donation");
            migrationBuilder.DropForeignKey(name: "FK_Message_Conversation_ConversationId", table: "Message");
            migrationBuilder.DropForeignKey(name: "FK_Photo_Donation_DonationId", table: "Photo");
            migrationBuilder.DropForeignKey(name: "FK_UserWhoAttending_AppEvent_AppEventId", table: "UserWhoAttending");
            migrationBuilder.DropForeignKey(name: "FK_UserWhoWantDonation_Donation_WantedDonationID", table: "UserWhoWantDonation");
            migrationBuilder.DropForeignKey(name: "FK_WantedDonation_User_UserWhoWantDonationID", table: "WantedDonation");
            migrationBuilder.DropColumn(name: "AppEventAttendingId", table: "CommentEvent");
            migrationBuilder.AddForeignKey(
                name: "FK_AppEvent_User_UserOwnerId",
                table: "AppEvent",
                column: "UserOwnerId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_AppEventAttending_User_UserWhoAttendingId",
                table: "AppEventAttending",
                column: "UserWhoAttendingId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_CommentDonation_Donation_DonationId",
                table: "CommentDonation",
                column: "DonationId",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_CommentEvent_AppEvent_EventID",
                table: "CommentEvent",
                column: "EventID",
                principalTable: "AppEvent",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_Donation_User_OwnerId",
                table: "Donation",
                column: "OwnerId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_Message_Conversation_ConversationId",
                table: "Message",
                column: "ConversationId",
                principalTable: "Conversation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_Photo_Donation_DonationId",
                table: "Photo",
                column: "DonationId",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_UserWhoAttending_AppEvent_AppEventId",
                table: "UserWhoAttending",
                column: "AppEventId",
                principalTable: "AppEvent",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_UserWhoWantDonation_Donation_WantedDonationID",
                table: "UserWhoWantDonation",
                column: "WantedDonationID",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
            migrationBuilder.AddForeignKey(
                name: "FK_WantedDonation_User_UserWhoWantDonationID",
                table: "WantedDonation",
                column: "UserWhoWantDonationID",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(name: "FK_AppEvent_User_UserOwnerId", table: "AppEvent");
            migrationBuilder.DropForeignKey(name: "FK_AppEventAttending_User_UserWhoAttendingId", table: "AppEventAttending");
            migrationBuilder.DropForeignKey(name: "FK_CommentDonation_Donation_DonationId", table: "CommentDonation");
            migrationBuilder.DropForeignKey(name: "FK_CommentEvent_AppEvent_EventID", table: "CommentEvent");
            migrationBuilder.DropForeignKey(name: "FK_Donation_User_OwnerId", table: "Donation");
            migrationBuilder.DropForeignKey(name: "FK_Message_Conversation_ConversationId", table: "Message");
            migrationBuilder.DropForeignKey(name: "FK_Photo_Donation_DonationId", table: "Photo");
            migrationBuilder.DropForeignKey(name: "FK_UserWhoAttending_AppEvent_AppEventId", table: "UserWhoAttending");
            migrationBuilder.DropForeignKey(name: "FK_UserWhoWantDonation_Donation_WantedDonationID", table: "UserWhoWantDonation");
            migrationBuilder.DropForeignKey(name: "FK_WantedDonation_User_UserWhoWantDonationID", table: "WantedDonation");
            migrationBuilder.AddColumn<int>(
                name: "AppEventAttendingId",
                table: "CommentEvent",
                nullable: true);
            migrationBuilder.AddForeignKey(
                name: "FK_AppEvent_User_UserOwnerId",
                table: "AppEvent",
                column: "UserOwnerId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_AppEventAttending_User_UserWhoAttendingId",
                table: "AppEventAttending",
                column: "UserWhoAttendingId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_CommentDonation_Donation_DonationId",
                table: "CommentDonation",
                column: "DonationId",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_CommentEvent_AppEventAttending_AppEventAttendingId",
                table: "CommentEvent",
                column: "AppEventAttendingId",
                principalTable: "AppEventAttending",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_CommentEvent_AppEvent_EventID",
                table: "CommentEvent",
                column: "EventID",
                principalTable: "AppEvent",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_Donation_User_OwnerId",
                table: "Donation",
                column: "OwnerId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_Message_Conversation_ConversationId",
                table: "Message",
                column: "ConversationId",
                principalTable: "Conversation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_Photo_Donation_DonationId",
                table: "Photo",
                column: "DonationId",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_UserWhoAttending_AppEvent_AppEventId",
                table: "UserWhoAttending",
                column: "AppEventId",
                principalTable: "AppEvent",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_UserWhoWantDonation_Donation_WantedDonationID",
                table: "UserWhoWantDonation",
                column: "WantedDonationID",
                principalTable: "Donation",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
            migrationBuilder.AddForeignKey(
                name: "FK_WantedDonation_User_UserWhoWantDonationID",
                table: "WantedDonation",
                column: "UserWhoWantDonationID",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
