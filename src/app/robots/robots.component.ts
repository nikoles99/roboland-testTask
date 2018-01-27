import {Component, OnInit} from '@angular/core';
import {RobotsService} from './robots.service';

@Component({
  selector: 'app-robots',
  templateUrl: './robots.component.html',
  styleUrls: ['./robots.component.css'],
})
export class RobotsComponent implements OnInit {
  robots = [];
  selectedRobot: any;
  task: string;
  message: string;

  constructor(private robotsService: RobotsService) {
  }

  ngOnInit() {
    this.robotsService.getRobots().subscribe(data => this.pushRobots(data));
    const that = this;
    this.robotsService.initializeWebSocketConnection('/robots', function (message) {
      if (message.body) {
        const data = JSON.parse(message.body);
        that.robots = [];
        that.robots.push(null);
        data.forEach(r => that.robots.push(r));
      }
    });
  }

  addTaskToAllRobots() {
    if (this.validate()) {
      this.robotsService.addTaskToAllRobots(this.task);
      this.message = 'Task \'' + this.task + '\' added in queue to all robots.';
      this.selectedRobot = null;
      this.task = null;
    }
  }

  addTask() {
    if (this.validate()) {
      this.robotsService.addTask(this.task, this.selectedRobot);
      this.message = 'Task \'' + this.task + '\' added in queue to robot ' + this.selectedRobot + '.';
      this.selectedRobot = null;
      this.task = null;
    }
  }

  setSelectedRobot(robot) {
    this.selectedRobot = robot;
  }

  private pushRobots(data) {
    this.robots = [];
    this.robots.push(null);
    data.forEach(r => this.robots.push(r));
  }

  private validate(): boolean {
    if (!this.selectedRobot) {
      this.message = 'Please select robot.';
      return false;
    }
    if (!this.task || this.task.length === 0) {
      this.message = 'Please input task.';
      return false;
    }
    return true;
  }
}
