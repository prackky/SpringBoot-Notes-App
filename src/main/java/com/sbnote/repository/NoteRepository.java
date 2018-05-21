package com.sbnote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sbnote.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	List<Note> findByUserId(String userId);
	
	@Query("select n from Note n where n.noteId = ?1 and n.userId = ?2")
	Note findByNoteIdOrUserId(Long noteId, String userId);
}
