package com.example.lr4_oop_4sem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "studentServlet" ,value="/student")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "C:\\Users\\gstit\\IdeaProjects\\lr3_OOP_JSON\\src\\main\\java\\student.json"; // Путь к файлу на сервере

    //    private List<Student> students;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
            students = gson.fromJson(reader, listType);
            response.getWriter().write(gson.toJson(students));
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при чтении списка студентов");
        }
    }
    //22
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при чтении запроса");
            return;
        }

        Gson gson = new GsonBuilder().create();
        Student student = gson.fromJson(jsonRequest.toString(), Student.class);

        // Чтение текущего списка студентов из файла
        List<Student> students = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<Student>>(){}.getType();
            students = gson.fromJson(fileReader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Добавление нового студента в список
        students.add(student);

        // Запись списка студентов обратно в файл
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            gson.toJson(students, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при записи студентов в файл");
            return;
        }

        // Отправка обновленного списка студентов
        doGet(request, response);
    }
}
