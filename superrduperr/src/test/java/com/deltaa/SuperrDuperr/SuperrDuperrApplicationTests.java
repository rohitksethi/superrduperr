package com.deltaa.SuperrDuperr;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.deltaa.superrduperr.entity.ToDoItem;
import com.deltaa.superrduperr.entity.ToDoList;
import com.deltaa.superrduperr.jparepository.ToDoItemJpaRepository;
import com.deltaa.superrduperr.jparepository.ToDoListJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SuperrDuperrApplicationTests {

	
	private MockMvc mockMvc;
	
	@Autowired
    WebApplicationContext wContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private ToDoItemJpaRepository todoitemRepository;
	
	@Autowired
	private ToDoListJpaRepository todolistRepository;

    @Before
    public void init() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(wContext).build();
    }
    
    
	@Test
	public void Given_ToDoList_uri_When_get_request_executed_Then_return_all_ToDoList() throws Exception {
		
		mockMvc.perform(get("/todolists"))
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].todoItems[0].id", is(1)))
				.andExpect(jsonPath("$[0].todoItems[1].id", is(2)));
	}
	
	@Test
	public void Given_app_running_When_new_ToDoList_name_is_specified_Then_Todolist_is_created() throws JsonProcessingException, Exception {
		
		ToDoList toDoList = new ToDoList();
		toDoList.setName("myToDoList");
        this.mockMvc.perform(post("/todolists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.name", is("myToDoList")));
		
	}
	
	@Test
	public void Given_ToDoList_uri_When_get_request_ById_is_executed_Then_return_ToDoList() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		int toDoListid = toDoList.getId();
		
		mockMvc.perform(get("/todolists/" + toDoListid))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(toDoListid)))
				.andExpect(jsonPath("$.name", is("newTodoList")));
		
		todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_uri_When_get_request_By_InValid_Id_is_executed_Then_return_NOT_FOUND() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
					
		mockMvc.perform(get("/todolists/145"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotFound());
		
		todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_When_ToDoItem_is_specified_Then_add_ToDoItem_in_ToDoList() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(jsonPath("$.taskname", is("new task")))
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_with_ToDoItem_When_ToDoItem_is_marked_deleted_Then_ToDoItem_is_deleted() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		toDoitem.setTodolist_id(toDoList.getId());
		toDoitem=todoitemRepository.save(toDoitem);
		
		toDoitem.setSoftdeleted(1);
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(jsonPath("$.taskname", is("new task")))
	                .andExpect(jsonPath("$.softdeleted", is(1)))
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_with_ToDoItem_When_ToDoItem_is_marked_restore_Then_ToDoItem_is_restored() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		toDoitem.setTodolist_id(toDoList.getId());
		toDoitem.setSoftdeleted(1);
		toDoitem=todoitemRepository.save(toDoitem);
		
		toDoitem.setSoftdeleted(0);
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(jsonPath("$.taskname", is("new task")))
	                .andExpect(jsonPath("$.softdeleted", is(0)))
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_with_ToDoItem_When_ToDoItem_is_marked_Complete_Then_ToDoItem_is_completed() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		toDoitem.setTodolist_id(toDoList.getId());
		toDoitem=todoitemRepository.save(toDoitem);
		
		toDoitem.setCompleted(1);
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(jsonPath("$.taskname", is("new task")))
	                .andExpect(jsonPath("$.completed", is(1)))
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_with_ToDoItem_When_ToDoItem_is_marked_tagged_Then_ToDoItem_is_tagged() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		toDoitem=todoitemRepository.save(toDoitem);
		
		toDoitem.setTag("Test Tag");;
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andExpect(jsonPath("$.taskname", is("new task")))
	                .andExpect(jsonPath("$.tag", is("Test Tag")))
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	@Test
	public void Given_ToDoList_with_ToDoItem_When_reminder_is_set_Then_reminder_is_set_on_ToDoItem() throws Exception {
		ToDoList toDoList = new ToDoList();
		toDoList.setName("newTodoList");
		toDoList= todolistRepository.save(toDoList);
		
		ToDoItem toDoitem = new ToDoItem();
		toDoitem.setTaskname("new task");
		toDoitem=todoitemRepository.save(toDoitem);
		Date df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2019-09-18 00:00:00.000");
		
		
		toDoitem.setReminder(df);
		
		MvcResult response = mockMvc.perform(post("/todolists/"+toDoList.getId()+"/todoitems")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(toDoitem)))
	                .andExpect(status().isOk())
	                .andDo(MockMvcResultHandlers.print())
	                .andReturn();

		 String json = response.getResponse().getContentAsString();
		 toDoitem = new ObjectMapper().readValue(json, ToDoItem.class);
		
		 assertEquals(df, toDoitem.getReminder());
		 todoitemRepository.delete(toDoitem);
	     todolistRepository.delete(toDoList);
		
	}
	
	

}
