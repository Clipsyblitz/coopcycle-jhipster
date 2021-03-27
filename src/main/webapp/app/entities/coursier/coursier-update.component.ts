import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICoursier, Coursier } from 'app/shared/model/coursier.model';
import { CoursierService } from './coursier.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-coursier-update',
  templateUrl: './coursier-update.component.html',
})
export class CoursierUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    surname: [null, [Validators.required]],
    transportMean: [null, [Validators.required]],
    phone: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('[0-9]*')]],
    userId: [],
  });

  constructor(
    protected coursierService: CoursierService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coursier }) => {
      this.updateForm(coursier);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(coursier: ICoursier): void {
    this.editForm.patchValue({
      id: coursier.id,
      name: coursier.name,
      surname: coursier.surname,
      transportMean: coursier.transportMean,
      phone: coursier.phone,
      userId: coursier.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const coursier = this.createFromForm();
    if (coursier.id !== undefined) {
      this.subscribeToSaveResponse(this.coursierService.update(coursier));
    } else {
      this.subscribeToSaveResponse(this.coursierService.create(coursier));
    }
  }

  private createFromForm(): ICoursier {
    return {
      ...new Coursier(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      surname: this.editForm.get(['surname'])!.value,
      transportMean: this.editForm.get(['transportMean'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoursier>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
