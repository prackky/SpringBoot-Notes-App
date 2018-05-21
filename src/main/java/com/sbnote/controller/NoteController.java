package com.sbnote.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbnote.exception.ResourceNotFoundException;
import com.sbnote.model.Note;
import com.sbnote.service.NoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value="Notes API end points")
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@ApiOperation(value = "View a list of all the notes saved by user", response = ResponseEntity.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
    @GetMapping(value = "/notes", produces = "application/json")
    public ResponseEntity<?> getAllNotesForUser(@ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) {
    	List<Note> notes = noteService.getAllNotes(userId);
    	return notes != null ? new ResponseEntity<List<Note>>(notes, HttpStatus.OK) 
    			: new ResponseEntity<String>("No note found for this user.", HttpStatus.CONFLICT);
    }

	@ApiOperation(value = "Saves a note in DB", response = ResponseEntity.class)
    @PostMapping(value = "/notes", produces = "application/json")
    public ResponseEntity<?> createNoteForUser(@Valid @RequestBody Note note, @ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) {
    	System.out.println("Saved by user: " + userId);
    	note.setUserId(userId);
    	Note saveNote = noteService.createNote(note);
    	if (saveNote ==  null) {
    		return new ResponseEntity<String>("There was some issue while saving the note...", HttpStatus.CONFLICT);
    	}
        return new ResponseEntity<Note>(saveNote, HttpStatus.CREATED);
    }

	@ApiOperation(value = "Search note with particular ID", response = ResponseEntity.class)
    @GetMapping(value = "/notes/{id}", produces = "application/json")
    public ResponseEntity<?> getNoteById(@PathVariable(value = "id") Long noteId, @ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) {
    	Note note = noteService.getNoteById(noteId, userId);
        return note != null ? new ResponseEntity<Note>(note, HttpStatus.OK)
    			: new ResponseEntity<String>("No note present for this id.", HttpStatus.CONFLICT);
    }

	@ApiOperation(value = "Search a product with an ID", response = ResponseEntity.class)
    @PutMapping(value = "/notes/{id}", produces = "application/json")
    public ResponseEntity<?> updateNoteForUser(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody Note noteDetails, @ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) throws ResourceNotFoundException {
    	Note updateNote = noteService.updateNote(noteId, userId, noteDetails);
    	return updateNote != null ? new ResponseEntity<Note>(updateNote, HttpStatus.OK) 
    			: new ResponseEntity<String>("No note present for this id, no updation occurred.", HttpStatus.CONFLICT);
    }

	@ApiOperation(value = "Delete note for particular ID", response = ResponseEntity.class)
    @DeleteMapping(value = "/notes/{id}", produces = "application/json")
    public ResponseEntity<?> deleteNoteForUser(@PathVariable(value = "id") Long noteId, @ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) throws ResourceNotFoundException {
    	noteService.deleteNote(noteId, userId);
    	return ResponseEntity.ok().build();
    }
    
	@ApiOperation(value = "Delete all notes for particular user", response = ResponseEntity.class)
    @DeleteMapping(value = "/notes/all", produces = "application/json")
    public ResponseEntity<?> deleteAllNoteForUser(@ApiParam(hidden = true) @AuthenticationPrincipal @RequestAttribute(name="userId") String userId) throws ResourceNotFoundException {
    	noteService.deleteAllNotes(userId);
    	return ResponseEntity.ok().build();
    }
}